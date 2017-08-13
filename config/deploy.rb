
set :host , "pi@192.168.0.8"
task :deploy do
	run_locally do
		execute 'mvn clean package'
		execute "scp deployments/telegram_sms_tty-1.0.jar #{fetch(:host)}:/home/pi/Desktop/"
	end
	on fetch(:host) do
		execute "kill -9 $(jps | awk '/telegram/ { print $1 }')"
	end
	run_locally do
		execute "mvn clean > /dev/null"
	end
end
