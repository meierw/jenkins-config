import jenkins.model.Jenkins

def mailer = Jenkins.get().getDescriptor(hudson.tasks.Mailer)

mailer.setSmtpHost('{{ jenkins_config_email_notification.smtp_server }}')
mailer.setDefaultSuffix('{{ jenkins_config_email_notification.default_user_email_suffix }}')

{% if jenkins_config_email_notification.smtp_auth_username != '' and jenkins_config_email_notification.smtp_auth_password != '' %}
mailer.setSmtpAuth('{{ jenkins_config_email_notification.smtp_auth_username }}', '{{ jenkins_config_email_notification.smtp_auth_password }}')
{% else %}
mailer.setSmtpAuth(null, null)
{% endif %}
mailer.setUseSsl({% if jenkins_config_email_notification.use_ssl %}true{% else %}false{% endif %})
mailer.setSmtpPort('{{ jenkins_config_email_notification.smtp_port }}')
mailer.setReplyToAddress('{{ jenkins_config_email_notification.reply_to_address }}')
mailer.setCharset('{{ jenkins_config_email_notification.charset }}')

mailer.save()