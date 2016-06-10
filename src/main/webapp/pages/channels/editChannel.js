var keep = ['credentialLabel'];
if (isBuiltin) {
	keep.push("channel(internalName)");
}

function updatePrincipalTypes() {
	var types = ensureArray(getValue("principalTypes"));
	var defaultPrincipalType = $('defaultPrincipalTypeSelect');
	clearOptions(defaultPrincipalType);
	var options = types.map(function(t) {
		return new Option(principalTypes[t].text, t);
	});
	setOptions(defaultPrincipalType, options);
	setValue(defaultPrincipalType, selectedDefaultPrincipalType);
}

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},

	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, keep);
			getObject('channel(displayName)').focus();
		}
	},
	
	'#defaultPrincipalTypeSelect': function(select) {
		select.onchange = function() {
			selectedDefaultPrincipalType = getValue(this);
		}
	},
	
	'#supportsPaymentRequest': function(check) {
		check.onclick = function() {
			$('trWS')[check.checked ? 'show' : 'hide']();
			if (!check.checked) {
				setValue("channel(paymentRequestWebServiceUrl)", "");
			}
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listChannels";
		}
	}
});

Event.observe(self, "load", function() {
	principalTypesSelect.values = principalTypes;
	principalTypesSelect.render();
	updatePrincipalTypes();
	if (isEmpty(getValue("channel(id)"))) {
		enableFormForInsert();
	}
	var spr = $('supportsPaymentRequest');
	if (spr) {
		spr.onclick();
	}
})