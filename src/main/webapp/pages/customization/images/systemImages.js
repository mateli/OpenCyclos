Behaviour.register({
	'a.showImage': function(a) {
		setPointer(a);
		a.onclick = function() {
			showImage(a.getAttribute("imageId"));
		}
	}
});