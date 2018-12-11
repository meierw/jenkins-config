import jenkins.model.Jenkins
import hudson.security.csrf.DefaultCrumbIssuer

def instance = Jenkins.get()

instance.setCrumbIssuer(
    {{ jenkins_config_csrf_enabled |bool|lower }} ?
    new DefaultCrumbIssuer(true) : null
)
instance.save()