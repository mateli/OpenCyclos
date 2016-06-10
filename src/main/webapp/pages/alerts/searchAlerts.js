function updateType() {
	var type = getValue("typeSelect");
	var isSystem = type == "SYSTEM";
	if (isSystem) {
		setValue("memberId", "");
		setValue("memberUsername", "");
		setValue("memberName", "");
	}
	Element[isSystem ? "hide" : "show"]("trMember");
}

Behaviour.register({
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#typeSelect': function(select) {
		select.onchange = updateType;
	}
});

Event.observe(self, "load", function() {
	getObject("query(period).begin").focus();
	updateType();
});