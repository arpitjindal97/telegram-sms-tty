
set :host , "pi@192.168.0.6"
task :deploy do
	run_locally do
		execute 'mvn clean package'
	end
	on fetch(:host) do
		upload! "deployments/telegram_sms_tty-1.0.jar","/home/pi/Desktop/"
		execute "kill -9 $(jps | awk '/telegram/ { print $1 }')"
	end
	run_locally do
		execute "mvn clean > /dev/null"
	end
end
