#!/bin/bash

# Base directory where your JARs are located
APP_DIR="/home/ec2-user"

# Define JARs and their corresponding profiles
declare -A services=(
  ["authservice-0.0.1-SNAPSHOT.jar"]="prod"

  # Add more services as needed
)

cd "$APP_DIR" || {
  echo "? Failed to navigate to $APP_DIR"
  exit 1
}

echo "?? Stopping existing Java services..."

# Kill running processes
for jar in "${!services[@]}"; do
  pid=$(pgrep -f "$jar")
  if [ -n "$pid" ]; then
    echo "?? Killing $jar with PID $pid"
    kill -9 "$pid"
  else
    echo "? $jar is not currently running."
  fi
done

echo "? Waiting for shutdown to complete..."
sleep 3

echo "?? Restarting services..."
# Restart services
for jar in "${!services[@]}"; do
  profile="${services[$jar]}"
  log_file="${jar%.jar}.log"
  echo "?? Starting $jar with profile $profile..."
  nohup java -Dspring.profiles.active=$profile -jar "$jar" > "$log_file" 2>&1 &
  echo "??  $jar started; logging to $log_file"
done

echo "? All services restarted successfully."
