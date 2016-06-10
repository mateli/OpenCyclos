function isInsert() {
	var id = parseInt(getValue("cardType(id)"));
	return (isNaN(id) || id == 0);
}
function updateFields(){
	var secCode = getValue("cardType(cardSecurityCode)");
	$$(".trSecCode").each(function(tr) {
		Element[secCode == 'NOT_USED' ? 'hide' : 'show'](tr);
	});
	$$(".trManualSecCode").each(function(tr) {
		Element[secCode == 'MANUAL' ? 'show' : 'hide'](tr);
	});
	
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/manageCardTypes";
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {	
			if (isInsert()) {
				enableFormFields.apply(button.form);
			}else{
				enableFormFields.apply(button.form, ["cardType(cardSecurityCode)", "cardType(showCardSecurityCode)"]);
			}
			getObject('cardType(name)').focus();
		}
	},
	
	'#defaultExpirationText': function(textarea) {
		new SizeLimit(textarea, 1000);
	},
	
	'.cardSecurityCodeSelect': function(select) {
		select.onclick = updateFields;					
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}
});

Event.observe(self, "load", function() {
	if (isInsert()) {
		enableFormForInsert();
	}
	updateFields();
});