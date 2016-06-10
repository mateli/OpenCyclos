
Behaviour.register({

	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/importedAdsSummary?importId=" + getValue("importId");
		}
	}

});
