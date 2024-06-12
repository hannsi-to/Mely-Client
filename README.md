# MelyClient
Minecraft Client
暇だから作ってみる

# ソースの構成
```
├src
｜├main
｜｜├java
｜｜｜└me
｜｜｜  └hannsi
｜｜｜    └melyclient
｜｜｜      ├command
｜｜｜      ｜├commands
｜｜｜      ｜｜├BindCommand.java
｜｜｜      ｜｜├HelpCommand.java
｜｜｜      ｜｜└PrefixCommand.java
｜｜｜      ｜└system
｜｜｜      ｜  ├ChatMessage.java
｜｜｜      ｜  └CommandBase.java
｜｜｜      ├event
｜｜｜      ｜├events
｜｜｜      ｜｜├LivingUpdateEvent.java
｜｜｜      ｜｜├MotionEvent.java
｜｜｜      ｜｜├PacketReadEvent.java
｜｜｜      ｜｜├PacketSendEvent.java
｜｜｜      ｜｜├Render2DEvent.java
｜｜｜      ｜｜├Render3DEvent.java
｜｜｜      ｜｜├UpdateMoveStateEvent.java
｜｜｜      ｜｜└UpdateWalkingPlayerEvent.java
｜｜｜      ｜└EventStage.java
｜｜｜      ├gui
｜｜｜      ｜├clickGui
｜｜｜      ｜｜├setting
｜｜｜      ｜｜｜├settings
｜｜｜      ｜｜｜｜├BindSetting.java
｜｜｜      ｜｜｜｜├BooleanSetting.java
｜｜｜      ｜｜｜｜├ColorSetting.java
｜｜｜      ｜｜｜｜├EnumSetting.java
｜｜｜      ｜｜｜｜├FloatSetting.java
｜｜｜      ｜｜｜｜├IntegerSetting.java
｜｜｜      ｜｜｜｜└StringSetting.java
｜｜｜      ｜｜｜└system
｜｜｜      ｜｜｜  ├SettingBase.java
｜｜｜      ｜｜｜  └SettingPanel.java
｜｜｜      ｜｜├CategoryPanel.java
｜｜｜      ｜｜├ClickGui.java
｜｜｜      ｜｜└ModulePanel.java
｜｜｜      ｜├clickGui2
｜｜｜      ｜｜├panels
｜｜｜      ｜｜｜├altManager
｜｜｜      ｜｜｜｜├AccountButton.java
｜｜｜      ｜｜｜｜├AccountScreen.java
｜｜｜      ｜｜｜｜├AltManagerScreen.java
｜｜｜      ｜｜｜｜├Circle.java
｜｜｜      ｜｜｜｜├InputAccountInfoBorder.java
｜｜｜      ｜｜｜｜└LoginModeButton.java
｜｜｜      ｜｜｜├clientInfo
｜｜｜      ｜｜｜｜├ClientInfo.java
｜｜｜      ｜｜｜｜└ClientInfoScreen.java
｜｜｜      ｜｜｜├console
｜｜｜      ｜｜｜｜└Console.java
｜｜｜      ｜｜｜└texture
｜｜｜      ｜｜｜  ├PopupMessage.java
｜｜｜      ｜｜｜  ├PopupMessageButton.java
｜｜｜      ｜｜｜  ├PopupMessageType.java
｜｜｜      ｜｜｜  ├ResourcePackButton.java
｜｜｜      ｜｜｜  ├Texture.java
｜｜｜      ｜｜｜  └TextureButton.java
｜｜｜      ｜｜├ClickGui2.java
｜｜｜      ｜｜├LoadingCircles.java
｜｜｜      ｜｜└Screen.java
｜｜｜      ｜└guiScreen
｜｜｜      ｜  ├mainMenu
｜｜｜      ｜  ｜├page
｜｜｜      ｜  ｜└CustomGuiMainMenu.java
｜｜｜      ｜  └splashProgress
｜｜｜      ｜    └SplashProgress.java
｜｜｜      ├manager
｜｜｜      ｜├AltManager.java
｜｜｜      ｜├CommandManager.java
｜｜｜      ｜├ConfigManager.java
｜｜｜      ｜├EventManager.java
｜｜｜      ｜├FontManager.java
｜｜｜      ｜├GitHubManager.java
｜｜｜      ｜└ModuleManager.java
｜｜｜      ├mixin
｜｜｜      ｜├net
｜｜｜      ｜｜├minecraft
｜｜｜      ｜｜｜├client
｜｜｜      ｜｜｜｜├entity
｜｜｜      ｜｜｜｜｜├IAbstractClientPlayer.java
｜｜｜      ｜｜｜｜｜├IEntity.java
｜｜｜      ｜｜｜｜｜├IEntityPlayerSP.java
｜｜｜      ｜｜｜｜｜└MixinEntityPlayerSP.java
｜｜｜      ｜｜｜｜├gui
｜｜｜      ｜｜｜｜｜└MixinGuiNewChat.java
｜｜｜      ｜｜｜｜├shader
｜｜｜      ｜｜｜｜｜├IShaderGroup.java
｜｜｜      ｜｜｜｜｜└MixinShaderGroup.java
｜｜｜      ｜｜｜｜├IMinecraft.java
｜｜｜      ｜｜｜｜└MixinMinecraft.java
｜｜｜      ｜｜｜├entity
｜｜｜      ｜｜｜｜└player
｜｜｜      ｜｜｜｜  └IEntityPlayer.java
｜｜｜      ｜｜｜├network
｜｜｜      ｜｜｜｜└MixinNetworkManager.java
｜｜｜      ｜｜｜└util
｜｜｜      ｜｜｜  ├ISession.java
｜｜｜      ｜｜｜  └MixinMovementInputFromOptions.java
｜｜｜      ｜｜└minecraftforge
｜｜｜      ｜｜  └fml
｜｜｜      ｜｜    └client
｜｜｜      ｜├org
｜｜｜      ｜｜└lwjgl
｜｜｜      ｜｜  └nanovg
｜｜｜      ｜｜    └MixinNanoVGGLConfig.java
｜｜｜      ｜└system
｜｜｜      ｜  └LoadingPlugin.java
｜｜｜      ├module
｜｜｜      ｜├modules
｜｜｜      ｜｜├client
｜｜｜      ｜｜｜└ClickGui.java
｜｜｜      ｜｜├combat
｜｜｜      ｜｜｜└TestCombat.java
｜｜｜      ｜｜├exploit
｜｜｜      ｜｜｜└TestExploit.java
｜｜｜      ｜｜├hud
｜｜｜      ｜｜｜└FPS.java
｜｜｜      ｜｜├misc
｜｜｜      ｜｜｜└TestMisc.java
｜｜｜      ｜｜├movement
｜｜｜      ｜｜｜├Fly.java
｜｜｜      ｜｜｜└Sprint.java
｜｜｜      ｜｜├player
｜｜｜      ｜｜｜└FastEat.java
｜｜｜      ｜｜└render
｜｜｜      ｜｜  └TestRender.java
｜｜｜      ｜└system
｜｜｜      ｜  ├settings
｜｜｜      ｜  ｜├Bind.java
｜｜｜      ｜  ｜├IEnumSetting.java
｜｜｜      ｜  ｜└Setting.java
｜｜｜      ｜  ├Category.java
｜｜｜      ｜  └Module.java
｜｜｜      ├util
｜｜｜      ｜├player
｜｜｜      ｜｜├movement
｜｜｜      ｜｜｜├fly
｜｜｜      ｜｜｜｜├Creative.java
｜｜｜      ｜｜｜｜└Vanilla.java
｜｜｜      ｜｜｜└MotionUtil.java
｜｜｜      ｜｜├PacketUtil.java
｜｜｜      ｜｜└PlayerUtil.java
｜｜｜      ｜├render
｜｜｜      ｜｜├guiScreen
｜｜｜      ｜｜｜└GuiScreenUtil.java
｜｜｜      ｜｜├nanovg
｜｜｜      ｜｜｜├render
｜｜｜      ｜｜｜｜├font
｜｜｜      ｜｜｜｜｜├Font.java
｜｜｜      ｜｜｜｜｜└FontUtil.java
｜｜｜      ｜｜｜｜├GradientMode.java
｜｜｜      ｜｜｜｜├NanoVGRenderUtil.java
｜｜｜      ｜｜｜｜└NanoVGUtil.java
｜｜｜      ｜｜｜└system
｜｜｜      ｜｜｜  ├Lwjgl2FunctionProvider.java
｜｜｜      ｜｜｜  └NanoVGSystemUtil.java
｜｜｜      ｜｜├render2D
｜｜｜      ｜｜｜├blur
｜｜｜      ｜｜｜｜├AbstractBufferedImageOp.java
｜｜｜      ｜｜｜｜├BlurUtil.java
｜｜｜      ｜｜｜｜├ConvolveFilter.java
｜｜｜      ｜｜｜｜└GaussianFilter.java
｜｜｜      ｜｜｜├particle
｜｜｜      ｜｜｜｜├Particle.java
｜｜｜      ｜｜｜｜└ParticleSystem.java
｜｜｜      ｜｜｜├texture
｜｜｜      ｜｜｜｜└Render2DTextureUtil.java
｜｜｜      ｜｜｜├Render2DShadowUtil.java
｜｜｜      ｜｜｜└Render2DUtil.java
｜｜｜      ｜｜├render3D
｜｜｜      ｜｜├shader
｜｜｜      ｜｜｜├GLSLSandboxShader.java
｜｜｜      ｜｜｜└ShaderUtil.java
｜｜｜      ｜｜├GLUtil.java
｜｜｜      ｜｜├IOUtil.java
｜｜｜      ｜｜└StencilUtil.java
｜｜｜      ｜├system
｜｜｜      ｜｜├auth
｜｜｜      ｜｜｜├AccountData.java
｜｜｜      ｜｜｜├HttpClient.java
｜｜｜      ｜｜｜├LoginFrame.java
｜｜｜      ｜｜｜├LoginMode.java
｜｜｜      ｜｜｜└MicrosoftWebView.java
｜｜｜      ｜｜├chat
｜｜｜      ｜｜｜├ChatData.java
｜｜｜      ｜｜｜└ChatUtil.java
｜｜｜      ｜｜├conversion
｜｜｜      ｜｜｜├AlignExtractor.java
｜｜｜      ｜｜｜├BonIcon.java
｜｜｜      ｜｜｜├DisplayUtil.java
｜｜｜      ｜｜｜└Keyboard.java
｜｜｜      ｜｜├debug
｜｜｜      ｜｜｜├DebugLevel.java
｜｜｜      ｜｜｜├DebugLog.java
｜｜｜      ｜｜｜└DebugType.java
｜｜｜      ｜｜├file
｜｜｜      ｜｜｜├FileUtil.java
｜｜｜      ｜｜｜└ModsUtil.java
｜｜｜      ｜｜├github
｜｜｜      ｜｜｜├CommitStats.java
｜｜｜      ｜｜｜├GitHubCommitStatistics.java
｜｜｜      ｜｜｜├GitHubCommitUtil.java
｜｜｜      ｜｜｜└GitHubUtil.java
｜｜｜      ｜｜├math
｜｜｜      ｜｜｜├animation
｜｜｜      ｜｜｜｜├Easing.java
｜｜｜      ｜｜｜｜└EasingUtil.java
｜｜｜      ｜｜｜├color
｜｜｜      ｜｜｜｜├theme
｜｜｜      ｜｜｜｜｜├clickGui
｜｜｜      ｜｜｜｜｜｜└ThemeClickGui.java
｜｜｜      ｜｜｜｜｜└parts
｜｜｜      ｜｜｜｜｜  ├Border.java
｜｜｜      ｜｜｜｜｜  ├Font.java
｜｜｜      ｜｜｜｜｜  └Line.java
｜｜｜      ｜｜｜｜├ColorUtil.java
｜｜｜      ｜｜｜｜└MChatFormatting.java
｜｜｜      ｜｜｜├crypto
｜｜｜      ｜｜｜｜└CryptoUtil.java
｜｜｜      ｜｜｜├time
｜｜｜      ｜｜｜｜├TimeCalculator.java
｜｜｜      ｜｜｜｜└TimerUtil.java
｜｜｜      ｜｜｜├vector
｜｜｜      ｜｜｜｜└Vector2f.java
｜｜｜      ｜｜｜└MathUtil.java
｜｜｜      ｜｜├ClipboardUtil.java
｜｜｜      ｜｜├ListUtil.java
｜｜｜      ｜｜├MouseUtil.java
｜｜｜      ｜｜└StringUtil.java
｜｜｜      ｜└InterfaceMinecraft.java
｜｜｜      └MelyClient.java
｜｜└resources
｜｜  ├assets
｜｜  ｜└minecraft
｜｜  ｜  └mely
｜｜  ｜    ├font
｜｜  ｜    ｜├NotoSansJP-Regular.ttf
｜｜  ｜    ｜├Ubuntu-M.ttf
｜｜  ｜    ｜└hannsi-BonIcon2.3.ttf
｜｜  ｜    ├img
｜｜  ｜    ｜├clickgui2
｜｜  ｜    ｜｜└microsoft.jpg
｜｜  ｜    ｜├mainmenu
｜｜  ｜    ｜｜├design.png
｜｜  ｜    ｜｜├wave1.png
｜｜  ｜    ｜｜└wave2.png
｜｜  ｜    ｜├wave1.png
｜｜  ｜    ｜└wave2.png
｜｜  ｜    └render
｜｜  ｜      ├blur
｜｜  ｜      ｜└blur.json
｜｜  ｜      └shader
｜｜  ｜        ├shaders
｜｜  ｜        ｜├mainmenu
｜｜  ｜        ｜｜├fireworks.fsh
｜｜  ｜        ｜｜├smoke.fsh
｜｜  ｜        ｜｜├wave1.fsh
｜｜  ｜        ｜｜└wave2.fsh
｜｜  ｜        ｜└shader2.fsh
｜｜  ｜        └passthrough.vsh
｜｜  ├mcmod.info
｜｜  ├mely_at.cfg
｜｜  ├mixins.melyclient.json
｜｜  └pack.mcmeta
｜└test
｜  ├java
｜  └resources
├.gitignore
├LICENSE
├README.md
├build.gradle
├gradle.properties
├gradlew
└gradlew.bat
```