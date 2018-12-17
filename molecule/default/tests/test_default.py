import os

import testinfra.utils.ansible_runner

testinfra_hosts = testinfra.utils.ansible_runner.AnsibleRunner(
    os.environ['MOLECULE_INVENTORY_FILE']).get_hosts('all')


def test_jenkins(host):
    output_switch = {
        'install': 'true',
        'skip': 'false'
    }
    desired_output = output_switch.get(os.environ['CONVERGE'], 'false')

    script_dir = os.path.dirname(os.path.realpath(__file__)) + '/scripts'
    for filename in os.listdir(script_dir):
        output = host.check_output(get_script_curl(filename))
        assert output == desired_output


def get_script_curl(script_file):
    return (
        'curl -u admin:admin ' +
        '--data-urlencode "script=$(cat ' + os.environ['JENKINS_TEST_SCRIPT_DIR'] + '/' + script_file + ')" ' +
        'http://localhost:8080/scriptText'
    )
