import jenkins.model.JenkinsLocationConfiguration

def config = JenkinsLocationConfiguration.get()
config.setUrl('{{ jenkins_config_jenkins_url }}')
config.save()