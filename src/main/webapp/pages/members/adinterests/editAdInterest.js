Behaviour.register({
	
	'#backButton': function(button) {
		button.onclick = function() {
			var params = $H();
			params.set('memberId', getValue("adInterest(owner)"));
			backToLastLocation(params);
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, []);
			getObject('adInterest(title)').focus();
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true}, 'memberId', 'memberUsername', 'memberName');
	},

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}
	
});

Event.observe(self, "load", function() {
	if (isEmpty(getValue("adInterest(id)"))) {
		enableFormForInsert();
	}
})