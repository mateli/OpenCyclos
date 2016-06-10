Behaviour.register({

	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + getValue("query(member)");
		}
	}
	
});