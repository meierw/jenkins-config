import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.flow.GlobalDefaultFlowDurabilityLevel
import org.jenkinsci.plugins.workflow.flow.FlowDurabilityHint

def instance = Jenkins.get()
def descriptor = instance.getDescriptor(GlobalDefaultFlowDurabilityLevel)

descriptor.setDurabilityHint(FlowDurabilityHint.{{ jenkins_config_pipeline_durability | upper }})
descriptor.save()