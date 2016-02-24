#!/usr/bin/python

from PyQt4 import QtCore, QtGui

DEFAULT_MODID = "futurecraft"

class Window(QtGui.QDialog):
    def __init__(self, parent=None):
        super(Window, self).__init__(parent)

        browseAssetsButton = self.createButton("&Browse...", self.browseAssets)
        browseTexturesButton = self.createButton("&Browse...", self.browseTextures)
        makeButton = self.createButton("Make", self.make)

        self.id = QtGui.QLineEdit("")
        self.modId = QtGui.QLineEdit(DEFAULT_MODID)
        self.assets = QtGui.QLineEdit(QtCore.QDir.currentPath())
        self.texture = QtGui.QLineEdit("")

        idLabel = QtGui.QLabel("Block ID:")
        modIdLabel = QtGui.QLabel("Mod ID:")
        textureLabel = QtGui.QLabel("texture:")
        assetsLabel = QtGui.QLabel("assets folder:")
        self.filesFoundLabel = QtGui.QLabel()

        mainLayout = QtGui.QGridLayout()
        
        mainLayout.addWidget(idLabel, 0, 0)
        mainLayout.addWidget(self.id, 0, 1, 1, 2)

        mainLayout.addWidget(modIdLabel, 1, 0)
        mainLayout.addWidget(self.modId, 1, 1, 1, 2)

        mainLayout.addWidget(textureLabel, 2, 0)
        mainLayout.addWidget(self.texture, 2, 1)
        mainLayout.addWidget(browseTexturesButton, 2, 2)

        mainLayout.addWidget(assetsLabel, 3, 0)
        mainLayout.addWidget(self.assets, 3, 1)
        mainLayout.addWidget(browseAssetsButton, 3, 2)

        mainLayout.addWidget(makeButton, 4, 0, 1, 3)
        self.setLayout(mainLayout)

        self.setWindowTitle("JSON Make")
        self.resize(500, 100)

    def browseAssets(self):
        directory = QtGui.QFileDialog.getExistingDirectory(self, "Find Files",
                QtCore.QDir.currentPath())

        if directory:
            self.assets.setText(directory)

    def browseTextures(self):
        directory = QtGui.QFileDialog.getOpenFileName(self, "Find Files",
                str(self.assets.text()) + "/" + str(self.modId.text()) + "/textures")

        print(str(self.assets.text()) + "/" + str(self.modId.text()) + "/textures")

        if directory:
            texture = directory.split("textures/")[1]
            self.texture.setText(texture)

    def make(self):
        print(str(self.assets.text()) + "/model.json")
        modelFile = file(str(self.assets.text()) + "/" + str(self.modId.text()) + "/models/block/" +  str(self.id.text()) + ".json", "w")
        modelFile.write(blockModelTemplate % (str(self.modId.text()), str(self.texture.text()).replace(str(self.assets.text()) + "/", "")))
        modelFile.close()

        blockStateFile = file(str(self.assets.text()) + "/" + str(self.modId.text()) + "/blockstates/" +  str(self.id.text()) + ".json", "w")
        blockStateFile.write(blockStateTemplate % (str(self.modId.text()), str(self.id.text())))
        blockStateFile.close()

        blockItemFile = file(str(self.assets.text()) + "/" + str(self.modId.text()) + "/models/item/" +  str(self.id.text()) + ".json", "w")
        blockItemFile.write(blockItemTemplate % (str(self.modId.text()), str(self.id.text())))
        blockItemFile.close()

        self.done(0)

    def createButton(self, text, member):
        button = QtGui.QPushButton(text)
        button.clicked.connect(member)
        return button

blockModelTemplate = """{
    "parent": "block/cube_all",
    "textures": {
        "all": "%s:%s"
    }
}"""

blockStateTemplate = """{
    "variants": {
        "normal": { "model": "%s:%s" }
    }
}"""

blockItemTemplate = """{
    "parent": "%s:block/%s",
    "display": {
        "thirdperson": {
            "rotation": [ 10, -45, 170 ],
            "translation": [ 0, 1.5, -2.75 ],
            "scale": [ 0.375, 0.375, 0.375 ]
        }
    }
}"""

if __name__ == '__main__':

    import sys

    app = QtGui.QApplication(sys.argv)
    window = Window()
    window.show()
    sys.exit(app.exec_())
