Behaviour.register({
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#backButton': function(element) {
		element.onclick = function() {
			var params = new QueryStringMap();
			if (booleanValue(params.get("fromQuickAccess"))) {
				self.location = pathPrefix + "/home";
			} else {
				history.back();
			}
		}
	},

	'img.edit': function(img) {
		img.onclick = function() {
			self.location = pathPrefix + "/editContact?id=" + img.getAttribute("contactId");
		}
		setPointer(img);
	},
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	'img.remove': function(img) {
		img.onclick = function() {
			if (confirm(deleteConfirmationMessage)) {
				self.location = pathPrefix + "/deleteContact?id=" + img.getAttribute("contactId");
			}
		};
		setPointer(img);
	}
});