var innerHeight = 0;
var outerHeight = null;
if (typeof( window.innerWidth ) == 'number') {
	innerHeight = window.innerHeight;
	outerHeight = window.outerHeight;
} else if (document.documentElement && (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
	innerHeight = document.documentElement.clientHeight;
} else if (document.body && (document.body.clientWidth || document.body.clientHeight)) {
	innerHeight = document.body.clientHeight;
}
if (outerHeight == null) {
	outerHeight = innerHeight + 60;
}
var heightOffset = outerHeight - innerHeight + 40;
var lastThumbnail = null;

function resizeWindow() {
	var thumbnailsHeight = 80;
	var minWidth = 275;
	var minHeight = 210;
	if (showThumbnails) {
		minHeight += thumbnailsHeight;
	}
	
	var maxWidth = Math.round(screen.width * 0.7);
	var maxHeight = Math.round(screen.height * 0.6);
	if (maxWidth == 0) {
		//Fix for opera on Linux
		maxWidth = screen.width - 100;
		maxHeight = screen.height - 70;
	}
	var img = $("popupImage");
	
	var dim = Element.getDimensions(img);

	if (dim.height >= maxHeight) {
		dim.width = Math.ceil((maxHeight * dim.width) / dim.height);
		dim.height = maxHeight;
	}
	if (dim.width > maxWidth) {
		dim.height = Math.ceil((maxWidth * dim.height) / dim.width);
		dim.width = maxWidth;
	}
	img.style.width = dim.width + "px";
	img.style.height = dim.height + "px";
	img.style.visibility = "visible";
	
	var calculatedWidth = Math.max(minWidth, dim.width + 80);
	
	//When there is a thumbnail bar, ensure it's visible
	if (lastThumbnail != null) {
		var lastThumbPosition = Position.positionedOffset(lastThumbnail)[0];
		var lastThumbWidth = Element.getDimensions(lastThumbnail).width;
		var thumbTotalWidth = lastThumbPosition + lastThumbWidth + 20;  
		if (calculatedWidth < thumbTotalWidth) {
			calculatedWidth = thumbTotalWidth;
		}
	}

	//Resize the window to fit the image
	var containerHeight = Element.getDimensions('containerDiv').height;
	var windowWidth = Math.min(maxWindowWidth, calculatedWidth);
	window.resizeTo(windowWidth, containerHeight + heightOffset);
}

Behaviour.register({
	'#closeButton': function(button) {
		button.onclick = function() {
			window.close();
		}
	},
		
	'#thumbnailContainer': function(div) {
		window.opener.imageContainer.imageDescriptors.each(function(descriptor) {
			var caption = descriptor.caption;
			var img = document.createElement("img");
			img.className = "popupThumbnail"
			img.src = descriptor.url;
			img.alt = caption;
			img.title = caption;			
			img = div.appendChild(img);
			setPointer(img);
			img.onclick = function() {
				var id = descriptor.id;
				var img = $('popupImage');
				img.style.visibility = "hidden";
				img.style.width = "";
				img.style.height = "";
				img.src = context + "/image?id=" + id;
				$('caption').innerHTML = caption;
			}
			lastThumbnail = img;
		});
	},
	
	'.footerNote': function(div) {
		try {
			descriptor = window.opener.imageContainer.currentImageDescriptor();
			$('caption').innerHTML = descriptor.caption;
		} catch (e) {}
	},
	
	'#popupImage': function(img) {
		img.onload = resizeWindow;
	}
});

