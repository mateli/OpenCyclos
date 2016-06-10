function updateRecipient() {
	var recipient = getValue("query(recipient)");
	$$(".trGroup").each(function(tr) {
		tr[recipient == "GROUPS" ? 'show' : 'hide']();
	});
	$$(".trMember").each(function(tr) {
		tr[recipient == "MEMBER" ? 'show' : 'hide']();
	});
}
Behaviour.register({

	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/sendSmsMailing";
		}
	},
	
	'.recipient': function(radio) {
		radio.onclick = function() {
			updateRecipient();
			if (radio.value != 'MEMBER') {
				setValue("memberId", "");
				setValue("memberUsername", "");
				setValue("memberName", "");
			}
			if (radio.value != 'GROUPS') {
				setValue("query(group)", "");
			}
		}
	},

	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, brokered:true}, 'memberId', 'memberUsername', 'memberName', 'text');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, brokered:true}, 'memberId', 'memberUsername', 'memberName', 'text');
	}	
	
});

Event.observe(self, "load", updateRecipient);