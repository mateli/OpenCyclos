
Behaviour.register({

	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/importedMembersSummary?importId=" + getValue("importId");
		}
	}

});
