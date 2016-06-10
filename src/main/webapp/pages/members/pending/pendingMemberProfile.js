Behaviour.register({
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ['creationDateText', 'groupText', 'resendEmailButton']);
			(getObject('member(username)') || getObject('pendingMember(name)')).focus();
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/searchPendingMembers";
		}
	},
	
	'#resendEmailButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/resendEmailValidation?pendingMemberId=" + getValue("pendingMemberId");
		}
	},
	
	'#brokerUsername': function(input) {
		var div = $('brokersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, brokers:true}, 'brokerId', 'brokerUsername', 'brokerName', 'comments');
	},

	'#brokerName': function(input) {
		var div = $('brokersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, brokers:true}, 'brokerId', 'brokerUsername', 'brokerName', 'comments');
	},

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'input': function(checkbox) {
		var prefix = "chk_hidden_";
		if (checkbox.id.indexOf(prefix) >= 0) {
			checkbox.onclick = function() {
				$('hidden_' + this.id.substring(prefix.length)).value = this.checked;
			}.bindAsEventListener(checkbox);
		}
	}
});