Behaviour.register({
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeCustomImage?imageId=" + img.getAttribute("imageId") + "&nature=" + nature;
			}
		}
	},
	
	'a.showImage': function(a) {
		setPointer(a);
		a.onclick = function() {
			showImage(a.getAttribute("imageId"));
		}
	}
});