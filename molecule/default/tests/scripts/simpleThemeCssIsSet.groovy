import org.codefirst.SimpleThemeDecorator
import org.jenkinsci.plugins.simpletheme.CssUrlThemeElement

print PageDecorator.all().get(SimpleThemeDecorator.class).getElements()
	.find { it.class == CssUrlThemeElement } ? true : false