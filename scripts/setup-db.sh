#!/usr/bin/env bash
MYSQL_USER=${1:-root}
MYSQL_PASS=${2:-}
DB_NAME=${3:-cinema2}

echo "Creating database '$DB_NAME' and importing seed files..."
echo "CREATE DATABASE IF NOT EXISTS $DB_NAME CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;" > /tmp/create_db.sql
mysql -u "$MYSQL_USER" -p"$MYSQL_PASS" < /tmp/create_db.sql
rm -f /tmp/create_db.sql

for f in seed_data.sql seed_news.sql seed_promotions.sql; do
  if [ -f "$f" ]; then
    echo "Importing $f..."
    mysql -u "$MYSQL_USER" -p"$MYSQL_PASS" "$DB_NAME" < "$f"
  else
    echo "Warning: $f not found"
  fi
done

echo "Done."
