Behaviour.register({
	'img.view': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/viewTransaction?showActions=true&transferId=" + img.getAttribute("transferId");
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName');
	}
});