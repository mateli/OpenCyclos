function isInsert() {
	var id = parseInt(getValue("setting(id)"));
	return (isNaN(id) || id == 0);
}

function updateTransferTypes() {
	var select;
	try {
		select = $('accountTypeSelect');
		if (select == null) throw new Error();
	} catch (e) {
		return;
	}
	
	var params = $H();
	params.set("context", "AUTOMATIC");
	params.set("fromNature", "SYSTEM");
	params.set("toAccountTypeId", getValue("accountTypeSelect"));
	findTransferTypes(params, function(tts) {
		setOptions($("initialCreditTTSelect"), tts, true, false, "name", "id");
	});
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editGroup?groupId=" + getValue("groupId");
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ['accountTypeText']);
			if (isInsert()) {
				getObject('setting(accountType)').focus();
			} else {
				getObject('setting(defaultCreditLimit)').focus();
			}
		}
	},
	
	'#accountTypeSelect': function(select) {
		select.onchange = updateTransferTypes;
	}
});

Event.observe(self, "load", function() {
	if (isInsert()) {
		updateTransferTypes();
		enableFormForInsert();
	}
});