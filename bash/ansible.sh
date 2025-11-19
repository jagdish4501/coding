inv=$HOME/.function/inv

cmd_as_user(){
    if [ $# -lt 3 ]; then
        echo -e "\e[31mUsage: cmd_as_user host1:host2 'command_string' user\e[0m"
        return 1
    fi
    read -sp "Become passwd :" passwd
    echo
    ansible $1 -i $inv -m shell -a "bash -lc \"cd &&  echo '===============================================================================================================' &&  $2\"" -become  --become-user=$3 --extra-vars ansible_become_pass=$passwd
}
copy() {
    if [ $# -lt 4 ]; then
        echo -e "\e[31mUsage: copy host1:host2 src_path dst_path user\e[0m"
        return 1
    fi
    read -sp "Become passwd :" passwd
    echo
    ansible $1 -i $inv -m copy -a "src=$2 dest=$3 force=true" -become  --become-user=$4 --extra-vars ansible_become_pass=$passwd
}

fetch(){
    if [ $# -lt 3 ]; then
        echo -e "\e[31mUsage: fetch host1:host2 src_path dst_path \e[0m"
        return 1
    fi
    read -sp "Become passwd :" passwd
    echo
    ansible $1 -i $inv -m fetch -a "src=$2 dest=$3" --become --become-user=root --extra-vars ansible_become_pass=$passwd
}

create_user() {
    if [ $# -lt 1 ]; then
        echo -e "\e[31mUsage: create_user host1:host2 \e[0m"
        return 1
    fi
    read -sp "Become passwd: " becom_passwd
    echo
    read -p "Enter the username to create: " username
    read -sp "Enter user password: " passwd
    echo
    read -p "Enter the public SSH key: " pubkey
    read -p "Enter email separated by space or skip to send mail: " to

    # Create user with privilege escalation
    ansible "$1" -i "$inv" -m user -a "name=$username password=$passwd state=present shell=/bin/bash" \
        --become --become-user=root --extra-vars "ansible_become_pass=$becom_passwd"

    # Add SSH public key
    ansible "$1" -i "$inv" -m authorized_key -a "user=$username key='$pubkey' state=present" \
        --become --become-user=root --extra-vars "ansible_become_pass=$becom_passwd"

    # Set user password explicitly via chpasswd
    ansible "$1" -i "$inv" -m shell -a "$1" "echo '$username:$passwd' | chpasswd" --become --become-user=root --extra-vars "ansible_become_pass=$becom_passwd"

    # Send mail if 'to' is not empty
    if [[ -n "$to" ]]; then
        send_mail "$to" "User created on the following servers: $1" \
            "Hi $to,\nYour username is: $username\nYour password is: $passwd"
    fi
}

send_mail() {
    local defaultcc="jagdish.kumar@gmail.com"
    local to="$1"
    local subject="$2"
    local body="$3"
    local cc="${4:-$defaultcc}"

    if [[ -z "$to" ]]; then
        echo "skipping email address not provided."
        return 1
    fi
    to="${to// /,}"
    cc="${cc// /,}"
    ansible localhost -m community.general.mail -a "host=smtp.gmail.com port=587 username='alerts@gmail.co' password='mfrm' to='$to' cc='$cc' from='alerts@gmail.co' subject='$subject' body='$body' secure=starttls"
}