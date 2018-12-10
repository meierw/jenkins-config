import jenkins.model.JenkinsLocationConfiguration

def config = JenkinsLocationConfiguration.get()
config.setAdminAddress('{{ jenkins_config_system_admin_email }}')
config.save()