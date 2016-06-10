Behaviour.register({
	'.edit': function(element) {
		setPointer(element);
		element.onclick = function() {
			self.location = pathPrefix + "/editGroupFilter?groupFilterId=" + element.getAttribute("groupFilterId");
		}
	},
	'.remove': function(element) {
		setPointer(element);
		element.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeGroupFilter?groupFilterId=" + element.getAttribute("groupFilterId");
			}
		}
	},
	'.view': function(element) {
		setPointer(element);
		element.onclick = function() {
			self.location = pathPrefix + "/editGroupFilter?groupFilterId=" + element.getAttribute("groupFilterId");
		}
	},
	'#newButton': function(button) {
		element.onclick = function() {
			self.location = pathPrefix + "/editGroupFilter";
		}
	}
});