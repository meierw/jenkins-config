import hudson.model.PageDecorator
import org.codefirst.SimpleThemeDecorator
import org.jenkinsci.plugins.simpletheme.CssUrlThemeElement

def decorator = PageDecorator.all().get(SimpleThemeDecorator.class)
decorator.setElements([new CssUrlThemeElement('{{ jenkins_config_simple_theme_css_url }}')])
decorator.save()