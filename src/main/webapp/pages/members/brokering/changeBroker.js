Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + button.getAttribute("memberId");
		}
	},

	'#brokerUsername': function(input) {
		var div = $('brokersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", viewableGroup: getValue('memberGroup'), enabled:true, brokers:true}, 'newBrokerId', 'brokerUsername', 'brokerName', 'comments');
	},

	'#brokerName': function(input) {		
		var div = $('brokersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", viewableGroup: getValue('memberGroup'), enabled:true, brokers:true}, 'newBrokerId', 'brokerUsername', 'brokerName', 'comments');
	},

	'#comments': function(textarea) {
		new SizeLimit(textarea, 4000);
	},

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}
});

Event.observe(self, "load", function() {
	$('brokerUsername').focus();
});