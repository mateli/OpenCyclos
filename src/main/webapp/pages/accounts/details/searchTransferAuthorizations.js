Behaviour.register({
	'img.view': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/viewTransaction?transferId=" + img.getAttribute("transferId");
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + getValue("memberId");
		}
	},
	
	'#adminUsername': function(input) {
		var div = $('adminsByUsername');
		prepareForAdminAutocomplete(input, div, {paramName:"username"}, 'adminId', 'adminUsername', 'adminName');
	},
	
	'#adminName': function(input) {
		var div = $('adminsByName');
		prepareForAdminAutocomplete(input, div, {paramName:"name"}, 'adminId', 'adminUsername', 'adminName');
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