@echo off
echo Loading environment variables from .env...
for /f "tokens=1,2 delims==" %%a in ('findstr "^[^#]" .env 2^>nul') do (
    set "%%a=%%b"
)

echo PGHOST=%PGHOST%
echo PGUSER=%PGUSER%
echo PGDATABASE=%PGDATABASE%

echo.
echo Testing connection with psql (if installed)...
psql "sslmode=require host=%PGHOST% port=5432 dbname=%PGDATABASE% user=%PGUSER% password=%PGPASSWORD%" -c "SELECT 'Connection OK' as status;"

pause
