import jenkins.model.Jenkins

def instance = Jenkins.get()

instance.setCrumbIssuer(
    {{ jenkins_config_csrf_enabled |bool|lower }} ?
    new hudson.security.csrf.DefaultCrumbIssuer(true) : null
)
instance.save()