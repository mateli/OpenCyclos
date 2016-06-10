Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			//self.location = pathPrefix + "/memberAds?memberId=" + getValue("ad(owner)");
			history.back();
		}
	}
});