import jenkins.model.Jenkins
import hudson.slaves.EnvironmentVariablesNodeProperty
import hudson.EnvVars

//Clearing previous Global Environment variables
def instance = Jenkins.get()
def gnpList = instance.getGlobalNodeProperties()
gnpList.removeAll(EnvironmentVariablesNodeProperty.class)

//Creating new ones
def evnp = new EnvironmentVariablesNodeProperty()
gnpList.add(evnp)
EnvVars evnVars = evnp.getEnvVars()

evnVars.overrideAll([
    {% for env_var in jenkins_config_global_environment_variables %}
        {% if env_var.name != '' %}'{{ env_var.name }}':'{{ env_var.value }}',{% endif %}
    {% endfor %}
])

instance.save()