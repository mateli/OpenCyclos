Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			history.back();
		}
	},
	
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editAd?memberId=" + memberId;
		}
	},
	
	'.editAd': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editAd?memberId=" + memberId + "&id=" + img.getAttribute("adId");
		}
	},
	
	'.viewAd': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/viewAd?memberId=" + memberId + "&id=" + img.getAttribute("adId");
		}
	},
	
	'.removeAd': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeAd?memberId=" + memberId + "&id=" + img.getAttribute("adId");
			}
		}
	}
});