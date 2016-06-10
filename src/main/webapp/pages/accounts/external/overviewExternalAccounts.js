Behaviour.register({
	'img.import': function(img) {
		setPointer(img);
		img.title = importTooltip;
		img.onclick = function() {
			self.location = pathPrefix + "/searchTransferImports?externalAccountId="+ img.getAttribute("externalAccountId");
		}
	},
	'img.view': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/externalAccountHistory?externalAccountId=" + img.getAttribute("externalAccountId") + "&transferImportId=0";
		}
	}
});