set env=release
set brw=chrome
set log=C:\Selenium_TestData\Projects\DocsFlow\Other\BatLog\%brw%_%env%_run_%date:/=-%.log
echo start > %log%
echo ================ >> %log%

:: Запуск тестов
mvn clean test -D browser=%brw% -D environment=%env% >> %log%

echo ================ >> %log%
echo done >> %log%