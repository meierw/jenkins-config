import jenkins.model.Jenkins

import hudson.model.Node
import hudson.slaves.DumbSlave

List<Node> nodes = []
DumbSlave slave
{% for node in jenkins_config_nodes %}
    {% if node.name != '' and node.remote_root_directory != ''
        and node.usage|upper in ['NORMAL', 'EXCLUSIVE']
        and node.launch_method.type in ['via_ssh', 'via_command_on_master'] 
        and node.availability.type in ['always'] 
    %}
        slave = new DumbSlave(
            '{{ node.name }}',
            '{{ node.remote_root_directory }}',
            {% if node.launch_method.type == 'via_command_on_master' %}
                new hudson.slaves.CommandLauncher('''{{ node.launch_method.command }}''')
            {% elif node.launch_method.type == 'via_ssh' %}
                new hudson.plugins.sshslaves.SSHLauncher(
                    '{{ node.launch_method.host }}',
                    {{ node.launch_method.port }},
                    '{{ node.launch_method.credentials_id }}',
                    '', '', null, '', '', null, null, null,
                    {% if node.launch_method.verification_strategy.type == 'known_hosts_file' %}
                        new hudson.plugins.sshslaves.verifiers.KnownHostsFileKeyVerificationStrategy()
                    {% elif node.launch_method.verification_strategy.type == 'manually_provided_key' %}
                        new hudson.plugins.sshslaves.verifiers.ManuallyProvidedKeyVerificationStrategy('{{ node.launch_method.verification_strategy.ssh_key }}')
                    {% elif node.launch_method.verification_strategy.type == 'manually_trusted_key' %}
                        new hudson.plugins.sshslaves.verifiers.ManuallyTrustedKeyVerificationStrategy({{ node.launch_method.verification_strategy.require_initial_manual_trust |bool|lower }})
                    {% elif node.launch_method.verification_strategy.type == 'non_verifying' %}
                        new hudson.plugins.sshslaves.verifiers.NonVerifyingKeyVerificationStrategy()
                    {% endif %}
                )
            {% endif %}
        )
        slave.setNodeDescription('{{ node.description }}')
        slave.setNumExecutors({{ node.num_of_executors }})
        slave.setLabelString('{{ node.labels }}')
        slave.setMode(Node.Mode.{{ node.usage | upper }})
        {% if node.availability.type == 'always' %}
            slave.setRetentionStrategy(new hudson.slaves.RetentionStrategy.Always())
        {% endif %}

        nodes.add(slave)
    {% endif %}
{% endfor %}

def instance = Jenkins.get()
instance.setNodes(nodes)
instance.save()
