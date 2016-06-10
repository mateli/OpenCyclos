Behaviour.register({
	'.edit': function(element) {
		setPointer(element);
		element.onclick = function() {
			self.location = pathPrefix + "/editAdCategory?id=" + element.getAttribute("categoryId");
		}
	},
	'.remove': function(element) {
		setPointer(element);
		element.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeAdCategory?id=" + element.getAttribute("categoryId");
			}
		}
	},
	'#newButton': function(button) {
		element.onclick = function() {
			self.location = pathPrefix + "/editAdCategory";
		}
	},
	'#changeOrderButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/setAdCategoryOrder";
		}
	},
	'.view': function(element) {
		setPointer(element);
		element.onclick = function() {
			self.location = pathPrefix + "/editAdCategory?id=" + element.getAttribute("categoryId");
		}
	}
});