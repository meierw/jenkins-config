import org.jenkinsci.plugins.workflow.flow.GlobalDefaultFlowDurabilityLevel

print Jenkins.get().getDescriptor(GlobalDefaultFlowDurabilityLevel)
    .getDurabilityHint() ? true : false