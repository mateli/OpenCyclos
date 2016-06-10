Behaviour.register({
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'img.loanGroupDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editLoanGroup?loanGroupId=" + img.getAttribute("loanGroupId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeLoanGroup?loanGroupId=" + img.getAttribute("loanGroupId");
			}
		}
	}
});

Event.observe(self, "load", function() {
	getObject("query(name)").focus();
});