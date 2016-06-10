
Behaviour.register({

	'#totalLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/importedMembersDetails";
		}
	},

	'#totalLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/importedMembersDetails?importId=" + getValue("importId") + "&status=ALL";
		}
	},

	'#successLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/importedMembersDetails?importId=" + getValue("importId") + "&status=SUCCESS";
		}
	},

	'#errorLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/importedMembersDetails?importId=" + getValue("importId") + "&status=ERROR";
		}
	},

	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/importMembers";
		}
	}

});
