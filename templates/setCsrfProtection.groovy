import jenkins.model.Jenkins
import hudson.security.csrf.DefaultCrumbIssuer

def instance = Jenkins.get()

instance.setCrumbIssuer(
    {% if jenkins_config_csrf_enabled %}true{% else %}false{% endif %} ?
    new DefaultCrumbIssuer(true) : null
)
instance.save()