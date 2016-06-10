Behaviour.register({
	'.adminProfileLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/adminProfile?adminId=" + a.getAttribute("adminId");
		}
	},
	
	'#newAdminGroup': function(select) {
		select.onchange = function() {
			var groupId = this.value;
			if (!isEmpty(groupId)) {
				self.location = pathPrefix + "/createAdmin?groupId=" + groupId
			}
		}
	}
});

Event.observe(self, "load", function() {
	getObject("query(keywords)").focus();
});