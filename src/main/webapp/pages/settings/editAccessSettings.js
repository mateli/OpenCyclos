function showFields() {
	var isGenerateLogin = getValue('generation') == "RANDOM";
	var callGenerate = isGenerateLogin ? 'show' : 'hide';
	var callManual = isGenerateLogin ? 'hide' : 'show';
	$$('.trManual').each(Element[callManual]);
	$$('.trGenerate').each(Element[callGenerate]);
}

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
		}
	},
	
	'#generation': function(button) {
		button.onchange = showFields;
	}
});

Event.observe(self, "load", showFields)