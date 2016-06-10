function isInsert() {
	var id = parseInt(getValue("fieldMapping(id)"));
	return (isNaN(id) || id == 0);
}

function switchMemberField() {
	if (getValue('fieldMapping(field)') == 'MEMBER_CUSTOM_FIELD') {
		Element['show']($('trMemberField'));	
	} else {
		Element['hide']($('trMemberField'));
	}
}

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#fieldSelect': function(select) {
		select.onchange = switchMemberField;
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editExternalAccount?externalAccountId=" + getValue("externalAccountId");
		}
	}
});

Event.observe(self, "load", function() {
	if (isInsert()) {
		enableFormForInsert();
	}
	switchMemberField();
});