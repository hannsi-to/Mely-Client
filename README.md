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
｜｜｜      ├event
｜｜｜      ｜├events
｜｜｜      ｜｜├LivingUpdateEvent.java
｜｜｜      ｜｜├MotionEvent.java
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
｜｜｜      ｜｜├Panels
｜｜｜      ｜｜｜└altManager
｜｜｜      ｜｜｜  ├AccountButton.java
｜｜｜      ｜｜｜  ├AccountScreen.java
｜｜｜      ｜｜｜  ├AltManagerScreen.java
｜｜｜      ｜｜｜  ├Circle.java
｜｜｜      ｜｜｜  ├InputAccountInfoBorder.java
｜｜｜      ｜｜｜  ├LoadingCircles.java
｜｜｜      ｜｜｜  └LoginModeButton.java
｜｜｜      ｜｜├ClickGui2.java
｜｜｜      ｜｜└Screen.java
｜｜｜      ｜└guiScreen
｜｜｜      ｜  ├mainMenu
｜｜｜      ｜  ｜├page
｜｜｜      ｜  ｜└CustomGuiMainMenu.java
｜｜｜      ｜  └splashProgress
｜｜｜      ｜    └SplashProgress.java
｜｜｜      ├manager
｜｜｜      ｜├AltManager.java
｜｜｜      ｜├EventManager.java
｜｜｜      ｜├FontManager.java
｜｜｜      ｜└ModuleManager.java
｜｜｜      ├mixin
｜｜｜      ｜├net
｜｜｜      ｜｜├minecraft
｜｜｜      ｜｜｜├client
｜｜｜      ｜｜｜｜├entity
｜｜｜      ｜｜｜｜｜├IEntity.java
｜｜｜      ｜｜｜｜｜└MixinEntityPlayerSP.java
｜｜｜      ｜｜｜｜├gui
｜｜｜      ｜｜｜｜├shader
｜｜｜      ｜｜｜｜｜├IShaderGroup.java
｜｜｜      ｜｜｜｜｜└MixinShaderGroup.java
｜｜｜      ｜｜｜｜├IMinecraft.java
｜｜｜      ｜｜｜｜└MixinMinecraft.java
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
｜｜｜      ｜｜｜｜└NVGRenderUtil.java
｜｜｜      ｜｜｜└system
｜｜｜      ｜｜｜  ├Lwjgl2FunctionProvider.java
｜｜｜      ｜｜｜  └NVGUtil.java
｜｜｜      ｜｜├render2D
｜｜｜      ｜｜｜├blur
｜｜｜      ｜｜｜｜├AbstractBufferedImageOp.java
｜｜｜      ｜｜｜｜├BlurUtil.java
｜｜｜      ｜｜｜｜├ConvolveFilter.java
｜｜｜      ｜｜｜｜└GaussianFilter.java
｜｜｜      ｜｜｜├particle
｜｜｜      ｜｜｜｜├Particle.java
｜｜｜      ｜｜｜｜└ParticleSystem.java
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
｜｜｜      ｜｜｜└LoginMode.java
｜｜｜      ｜｜├chat
｜｜｜      ｜｜｜├ChatData.java
｜｜｜      ｜｜｜└ChatUtil.java
｜｜｜      ｜｜├conversion
｜｜｜      ｜｜｜├BonIcon.java
｜｜｜      ｜｜｜└Keyboard.java
｜｜｜      ｜｜├debug
｜｜｜      ｜｜｜├DebugLevel.java
｜｜｜      ｜｜｜├DebugLog.java
｜｜｜      ｜｜｜└DebugType.java
｜｜｜      ｜｜└math
｜｜｜      ｜｜  ├animation
｜｜｜      ｜｜  ｜├Easing.java
｜｜｜      ｜｜  ｜└EasingUtil.java
｜｜｜      ｜｜  ├color
｜｜｜      ｜｜  ｜├theme
｜｜｜      ｜｜  ｜｜├clickGui
｜｜｜      ｜｜  ｜｜｜└ThemeClickGui.java
｜｜｜      ｜｜  ｜｜└parts
｜｜｜      ｜｜  ｜｜  ├Border.java
｜｜｜      ｜｜  ｜｜  ├Font.java
｜｜｜      ｜｜  ｜｜  └Line.java
｜｜｜      ｜｜  ｜└ColorUtil.java
｜｜｜      ｜｜  ├time
｜｜｜      ｜｜  ｜├TimeCalculator.java
｜｜｜      ｜｜  ｜└TimerUtil.java
｜｜｜      ｜｜  ├vector
｜｜｜      ｜｜  ｜└Vector2f.java
｜｜｜      ｜｜  ├DisplayUtil.java
｜｜｜      ｜｜  ├MathUtil.java
｜｜｜      ｜｜  ├MouseUtil.java
｜｜｜      ｜｜  └StringUtil.java
｜｜｜      ｜└InterfaceMinecraft.java
｜｜｜      └MelyClient.java
｜｜└resources
｜｜  ├assets
｜｜  ｜└minecraft
｜｜  ｜  └mely
｜｜  ｜    ├font
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