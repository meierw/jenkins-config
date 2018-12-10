import jenkins.model.Jenkins

import hudson.model.Node
import hudson.model.Node.Mode
import hudson.slaves.DumbSlave
// import hudson.slaves.CommandLauncher
// import hudson.plugins.sshslaves.SSHLauncher
// import hudson.plugins.sshslaves.verifiers.NonVerifyingKeyVerificationStrategy

List<Node> nodes = []
DumbSlave slave
{% for node in jenkins_config_nodes %}
    slave = new DumbSlave(
        '{{ node.name }}',
        '{{ node.remote_root_directory }}',
        {% if node.launch_method.type == 'via_command_on_master' %}
            new hudson.slaves.CommandLauncher('''{{ node.launch_method.command }}''')
        {% endif %}

        // new SSHLauncher(
        //     slaveName,                      //host
        //     22,                             //port
        //     '{{ node.name }}',   //credentialsId
        //     '',                             //jvmOptions
        //     '',                             //javaPath
        //     null,                           //jdkInstaller
        //     '',                             //prefixStartSlaveCmd
        //     '',                             //suffixStartSlaveCmd
        //     null,                           //launchTimeoutSeconds
        //     null,                           //maxNumRetries
        //     null,                           //retryWaitTime
        //     new NonVerifyingKeyVerificationStrategy()
        // )
    )
    slave.setNodeDescription('{{ node.description }}')
    slave.setNumExecutors({{ node.num_of_executors }})
    slave.setLabelString('{{ node.labels }}')
    slave.setMode(Mode.{{ node.usage }})
    {% if node.availability.type == 'always' %}
        slave.setRetentionStrategy(new hudson.slaves.RetentionStrategy.Always())
    {% endif %}

    nodes.add(slave)
{% endfor %}

def instance = Jenkins.get()
instance.setNodes(nodes)
instance.save()
