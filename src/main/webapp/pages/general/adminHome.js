function afterSelectMember(member) {
	if (member && member.id) {
		self.location = pathPrefix + "/profile?memberId=" + member.id;
	}
}

Behaviour.register({
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName', 'amount', afterSelectMember);
		input.onblur = function(input) {
			$('memberUsername').value = '';
		}
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName', 'amount', afterSelectMember);
		input.onblur = function(input) {
			$('memberUsername').value = '';
		}
	}
});


Event.observe(self, 'load', function() {
	getObject("memberUsername").focus();
});
