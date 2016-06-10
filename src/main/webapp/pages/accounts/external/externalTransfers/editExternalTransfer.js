function isInsert() {
	var id = parseInt(getValue("externalTransfer(id)"));
	return (isNaN(id) || id == 0);
}

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ['statusText', 'lineNumber', 'comments']);
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			var paramName = null;
			var paramValue = null;
			
			var transferImportId = parseInt(getValue("transferImportId"))
			if (!isNaN(transferImportId) && transferImportId != 0) {
				paramName = "transferImportId";
				paramValue = transferImportId;
			} else {
				var externalAccountId = parseInt(getValue("externalAccountId"))
				paramName = "externalAccountId";
				paramValue = externalAccountId;
			}
			self.location = pathPrefix + "/externalAccountHistory?" + paramName + "=" + paramValue;
		}
	}
});

Event.observe(self, "load", function() {
	if (isInsert()) {
		enableFormForInsert();
	}
});