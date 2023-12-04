#!/bin/bash

mysql -u root -proot tennis_event < "/docker-entrypoint-initdb.d/sql/01_create_schema.sql"

# ddlディレクトリのパス
ddl_directory="/docker-entrypoint-initdb.d/ddl"
# ddlディレクトリ内の全ての.sqlファイルを取得
sql_files=$(find "$ddl_directory" -type f -name "*.sql")
for sql_file in $sql_files; do
  echo "実行中: $sql_file"
  mysql -u root -proot tennis_event < "$sql_file"
done

mysql -u root -proot tennis_event < "/docker-entrypoint-initdb.d/sql/02_create_data.sql"
